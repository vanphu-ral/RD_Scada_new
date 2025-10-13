import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../core/auth/login/login.service'; // đường dẫn đúng của bạn
import { SharedModule } from '../../../share.module';
import { AccountService } from '../../core/auth/account/account.service';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [SharedModule],
  template: `
    <div class="login-page">
      <div class="container" style="display: flex; justify-content: center; align-items: center;">
        <div class="jumbotron1">
          <div class="flexDirection">
            <h3 style="padding: 20px; color: #ffc107;">Phần mềm quản lý sản xuất</h3>
          </div>
          <p class="lead" style="margin-top: 10px; font-size: 18px;">
            <i>Vui lòng thực hiện đăng nhập!</i>
          </p>
          <p style="margin-top: 30px; margin-bottom: 10px;">
            <button (click)="login()" class="btn btn-lg btn-warning">Đăng nhập</button>
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
export class HomeComponent implements OnInit {
  isLoggedIn = false;
  loading = true;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private router: Router
  ) { }

  ngOnInit(): void {
    // 🔄 kiểm tra nếu user đã đăng nhập thì redirect về trang chủ
    this.accountService.identity(false).subscribe({
      next: (account) => {
        this.isLoggedIn = !!account;
        this.loading = false;
        if (this.isLoggedIn) {
          this.router.navigate(['/']);
        }
      },
      error: () => {
        this.isLoggedIn = false;
        this.loading = false;
      }
    });
  }

  /** Gọi login flow (FE -> BE -> Keycloak) */
  login(): void {
    this.loginService.login();
  }

  goHome(): void {
    this.router.navigate(['/']);
  }
}
