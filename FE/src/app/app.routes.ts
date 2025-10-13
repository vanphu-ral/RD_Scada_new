import { Routes } from '@angular/router';
import { AppLayout } from './shared/layout/app.layout';
import { CallbackComponent } from './shared/core/auth/callback.component';
import { AuthGuard } from './shared/core/auth/auth.guard';
import { HomeComponent } from './shared/pages/Home/home.component';

export const routes: Routes = [
  {
    path: '',
    component: AppLayout,             // <- bảo vệ route chính
    loadChildren: () => import('./shared/pages/Monitor/monitor.routes')
  },
  {
    path: 'Traceability',
    component: AppLayout,             // nếu muốn bảo vệ Traceability
    loadChildren: () => import('./shared/pages/Traceability/traceability.routes')
  },
  { path: 'login', component: HomeComponent }, // trang login nội bộ
  { path: 'callback', component: CallbackComponent },
  // tùy chọn: redirect unknown
  { path: '**', redirectTo: '' }
];
