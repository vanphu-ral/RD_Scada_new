import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
    providedIn: 'root'
})
export class AppKeycloakService {
    constructor(private keycloakService: KeycloakService) { }

    async init() {
        await this.keycloakService.init({
            config: {
                url: 'http://192.168.68.90:8080/auth', 
                realm: 'QLSX',         
                clientId: 'scada_giam_sat',  
            },
            initOptions: {
                onLoad: 'login-required',
                checkLoginIframe: false
            },
            enableBearerInterceptor: true,
            bearerExcludedUrls: ['/assets']
        });
    }

    logout() {
        this.keycloakService.logout(window.location.origin);
    }

    getUserProfile() {
        return this.keycloakService.loadUserProfile();
    }

    getToken() {
        return this.keycloakService.getToken();
    }

    isLoggedIn() {
        return this.keycloakService.isLoggedIn();
    }
}
