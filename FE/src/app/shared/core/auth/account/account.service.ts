// import { inject, Injectable, Signal, signal } from '@angular/core';
// import { defer, Observable, ReplaySubject, of } from 'rxjs';
// import { KeycloakService } from 'keycloak-angular';
// import { Account } from './account.model';

// @Injectable({ providedIn: 'root' })
// export class AccountService {
//   private accountSignal = signal<Account | null>(null);
//   private authenticationState = new ReplaySubject<Account | null>(1);
//   private keycloakService = inject(KeycloakService);

//   identity(force = false): Observable<Account | null> {
//     if (this.accountSignal() && !force) {
//       return of(this.accountSignal());
//     }
//     return defer(async () => {
//       const isLoggedIn = await this.keycloakService.isLoggedIn();
//       if (!isLoggedIn) {
//         this.setAccount(null);
//         return null;
//       }
//       const profile: any = await this.keycloakService.loadUserProfile();
//       const token = await this.keycloakService.getToken();
//       const roles = this.keycloakService.getUserRoles ? this.keycloakService.getUserRoles() : [];
//       const account: Account = {
//         login: profile.username ?? profile.email ?? '',
//         firstName: profile.firstName ?? '',
//         lastName: profile.lastName ?? '',
//         email: profile.email ?? '',
//         fullName: `${profile.firstName ?? ''} ${profile.lastName ?? ''}`.trim(),
//         imageUrl: '',
//         activated: true,
//         langKey: 'vi',
//         authorities: Array.isArray(roles) ? roles.map(r => r.toUpperCase()) : [],
//         token
//       };
//       this.setAccount(account);
//       return account;
//     });
//   }

//   setAccount(account: Account | null): void {
//     this.accountSignal.set(account);
//     this.authenticationState.next(account);
//   }

//   trackAccount() { return this.accountSignal.asReadonly(); }
//   getAuthenticationState() { return this.authenticationState.asObservable(); }
//   getUser(): Account | null { return this.accountSignal(); }
//   isAuthenticated(): boolean { return this.accountSignal() !== null; }
//   hasAnyAuthority(authorities: string[] | string): boolean {
//     const userAuthorities = this.accountSignal()?.authorities ?? [];
//     if (!authorities) return true;
//     if (Array.isArray(authorities)) return authorities.some(a => userAuthorities.includes(a));
//     return userAuthorities.includes(authorities);
//   }
// }


import { inject, Injectable, Signal, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, ReplaySubject, of } from 'rxjs';
import { tap, shareReplay, catchError, map } from 'rxjs/operators';

import { Account } from './account.model';
import { ApplicationConfigService } from '../../config/application-config.service';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private accountSignal = signal<Account | null>(null);
  private authenticationState = new ReplaySubject<Account | null>(1);
  private accountCache$?: Observable<Account> | null;

  private http = inject(HttpClient);
  private appConfig = inject(ApplicationConfigService);

  /** Load account từ server */
  identity(force = false): Observable<Account | null> {
    if (!this.accountCache$ || force) {
      this.accountCache$ = this.http.get<Account>(
        this.appConfig.getEndpointFor('api/auth/user'),
        { withCredentials: true }
      ).pipe(
        tap(account => this.setAccount(account)),
        shareReplay(1)
      );
    }
    return this.accountCache$.pipe(catchError(() => of(null)));
  }


  /** Lưu account vào signal */
  setAccount(account: Account | null): void {
    this.accountSignal.set(account);
    this.authenticationState.next(account);
  }

  trackAccount(): Signal<Account | null> {
    return this.accountSignal.asReadonly();
  }

  /** 📌 Trả về account observable */
  getAuthenticationState(): Observable<Account | null> {
    return this.authenticationState.asObservable();
  }

  getUser(): Account | null {
    return this.accountSignal();
  }

  /** 📌 Kiểm tra đã login chưa */
  isAuthenticated(): boolean {
    return this.accountSignal() !== null;
  }

  /** 📌 Kiểm tra quyền */
  hasAnyAuthority(authorities: string[] | string): boolean {
    const userAuthorities = this.accountSignal()?.authorities ?? [];
    if (!authorities) {
      return true;
    }
    if (Array.isArray(authorities)) {
      return authorities.some(auth => userAuthorities.includes(auth));
    }
    return userAuthorities.includes(authorities);
  }
}
