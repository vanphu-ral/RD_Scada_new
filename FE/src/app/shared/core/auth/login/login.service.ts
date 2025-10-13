// import { Injectable } from '@angular/core';
// import { KeycloakService } from 'keycloak-angular';
// import { Router } from '@angular/router';
// import { AccountService } from '../account/account.service';
// import { firstValueFrom } from 'rxjs';

// @Injectable({ providedIn: 'root' })
// export class LoginService {
//   constructor(
//     private keycloakService: KeycloakService,
//     private accountService: AccountService,
//     private router: Router
//   ) { }

//   /** 🔐 Gọi login — nếu chưa đăng nhập sẽ redirect qua Keycloak */
//   async login(): Promise<void> {
//     const isLoggedIn = await this.keycloakService.isLoggedIn();
//     if (!isLoggedIn) {
//       await this.keycloakService.login({
//         redirectUri: window.location.origin, // sau khi login quay về FE
//       });
//     } else {
//       this.router.navigate(['/']);
//     }
//   }

//   /** 🚪 Logout khỏi Keycloak */
//   async logout(): Promise<void> {
//     await this.keycloakService.logout(window.location.origin);
//     this.accountService.setAccount(null);
//   }

//   /** 🧭 Sau khi app load lại, lấy thông tin user */
//   async handlePostLogin(): Promise<void> {
//     await firstValueFrom(this.accountService.identity(true)); // đợi load profile
//     // dọn URL
//     if (window.location.href.includes('#')) {
//       window.history.replaceState({}, document.title, window.location.origin);
//     }
//     this.router.navigate(['/']);
//   }

//   /** 🔄 Gọi trong app initializer để đảm bảo token sẵn sàng */
//   async restoreLoginFromStorage(): Promise<void> {
//     const isLoggedIn = await this.keycloakService.isLoggedIn();
//     if (isLoggedIn) {
//       const profile: any = await this.keycloakService.loadUserProfile();
//       this.accountService.setAccount(profile);
//     }
//   }
// }


import { Injectable } from '@angular/core';
import { AuthServerProvider, Logout } from '../auth-server.provider';
import { Router } from '@angular/router';
import { AccountService } from '../account/account.service';

@Injectable({ providedIn: 'root' })
export class LoginService {
  constructor(
    private authServerProvider: AuthServerProvider,
    private accountService: AccountService,
    private router: Router
  ) {}

  /** Redirect sang Keycloak login */
  login(): void {
    window.location.href = 'http://localhost:8080/oauth2/authorization/keycloak';
  }

  /** Logout */
  logout(): void {
    this.authServerProvider.logout().subscribe((logout: Logout) => {
      this.accountService.setAccount(null); // clear account khi logout
      window.location.href = logout.logoutUrl;
    });
  }

  /** Gọi sau khi app load lại từ Keycloak, fetch user info */
  handlePostLogin(): void {
    this.accountService.identity(true).subscribe({
      next: (account) => {
        if (account) {
          // Nếu muốn redirect về dashboard sau login
          this.router.navigate(['/']);
        }
      }
    });
  }

  restoreLoginFromStorage(): Promise<void> {
    return new Promise((resolve) => {
      this.accountService.identity(false).subscribe({
        next: () => resolve(),
        error: () => resolve()
      });
    });
  }
}
