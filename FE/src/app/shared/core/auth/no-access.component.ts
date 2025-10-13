import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login/login.service';

@Component({
    selector: 'app-no-access',
    template: `
    <div class="login-page">
      <div class="container" style="display: flex; justify-content: center; align-items: center;">
        <div class="jumbotron1">
          <div class="flexDirection">
            <h3 style="padding: 20px; color: #ffc107;">🚫 Bạn không có quyền truy cập hệ thống này</h3>
          </div>
          <p class="lead" style="margin-top: 10px; font-size: 18px;">
            <i>Vui lòng liên hệ quản trị viên để được cấp quyền.</i>
          </p>
          <p style="margin-top: 30px; margin-bottom: 10px;">
            <button (click)="logout()" class="btn btn-lg btn-warning">Đăng xuất</button>
          </p>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .login-page { 
      display:flex; 
      align-items:center; 
      justify-content:center; 
      height:100vh; 
      background: url(/assets/imgs/bg-rg.png) no-repeat center center fixed; 
      background-color: black; 
    }
    .jumbotron1 {
      display: flex;
      flex-direction: column;
      align-items: center;
      color: white;
      background-color: rgba(255, 255, 255, .15);
      -webkit-backdrop-filter: blur(3px);
      backdrop-filter: blur(3px);
      padding: 20px 40px;
      border-radius: 12px;
    }
  `]
})
export class NoAccessComponent {
    constructor(private loginService: LoginService, private router: Router) { }

    logout() {
        this.loginService.logout();
        this.router.navigate(['/login']);
    }
}
