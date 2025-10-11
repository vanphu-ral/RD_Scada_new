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
      <div class="container" style="display: flex; justify-content: center; align-items: center;" bis_skin_checked="1"><div class="jumbotron1" bis_skin_checked="1"><div class="flexDirection" bis_skin_checked="1"><h3 style="padding: 20px; color: #ffc107;">Phần mềm quản lý sản xuất</h3></div><p class="lead" style="margin-top: 10px; font-size: 18px;"><i>Vui lòng thực hiện đăng nhập!</i></p><p style="margin-top: 30px; margin-bottom: 10px;"><button (click)="login()" class="btn btn-lg btn-warning">Đăng nhập</button></p></div></div>
    </div>
  `,
  styles: [`
    .login-page { display:flex; align-items:center; justify-content:center; height:100vh; background: url(/assets/imgs/bg-rg.png) no-repeat center center fixed; background-color: black; }
    .jumbotron1 {
      display: flex;
      flex-direction: column;
      align-items: center;
      color: white;
      background-color: rgba(255, 255, 255, .15);
      -webkit-backdrop-filter: blur(3px);
      backdrop-filter: blur(3px);
    }
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
