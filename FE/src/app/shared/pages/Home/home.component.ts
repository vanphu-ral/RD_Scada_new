import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { Router } from '@angular/router';
import { LoginService } from '../../core/auth/login/login.service'; // chỉnh path nếu khác
import { SharedModule } from '../../../share.module';

@Component({
    selector: 'app-login-page',
    standalone: true,
    imports: [SharedModule],
    template: `
    <div class="login-page">
      <div class="login-card">
        <h2 class="text-dark">Đăng nhập</h2>
        <p *ngIf="loading">Kiểm tra trạng thái đăng nhập...</p>
        <p *ngIf="!loading && !isLoggedIn">Bạn cần đăng nhập để truy cập ứng dụng.</p>
        <p-button label="Đăng nhập" severity="info" (click)="login()" class="me-2"></p-button>
      </div>
    </div>
  `,
    styles: [`
    .login-page { display:flex; align-items:center; justify-content:center; height:100vh; }
    .login-card { padding:24px; border-radius:8px; background:#fff; box-shadow:0 4px 12px rgba(0,0,0,.08); text-align:center;}
  `]
})
export class HomeComponent implements OnInit {
    isLoggedIn = false;
    loading = true;

    constructor(
        private keycloak: KeycloakService,
        private loginService: LoginService,
        private router: Router
    ) { }

    async ngOnInit() {
        this.isLoggedIn = await this.keycloak.isLoggedIn();
        console.log(this.isLoggedIn);
        
        this.loading = false;
        if (this.isLoggedIn) {
            this.router.navigate(['/']);
        }
    }

    login() {
        this.loginService.login(); // sẽ redirect đến Keycloak
    }

    goHome() {
        this.router.navigate(['/']);
    }
}
