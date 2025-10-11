import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(private keycloak: KeycloakService, private router: Router) { }

    async canActivate(): Promise<boolean | UrlTree> {
        const isLoggedIn = await this.keycloak.isLoggedIn();
        if (isLoggedIn) {
            return true;
        }
        // nếu chưa login, chuyển về trang login nội bộ
        return this.router.parseUrl('/login');
    }
}
