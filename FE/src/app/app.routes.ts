import { Routes } from '@angular/router';
import { AppLayout } from './shared/layout/app.layout';
import { CallbackComponent } from './shared/core/auth/callback.component';
import { AuthGuard } from './shared/core/auth/auth.guard';
import { HomeComponent } from './shared/pages/Home/home.component';
import { NoAccessComponent } from './shared/core/auth/no-access.component';

export const routes: Routes = [
  {
    path: '',
    component: AppLayout,
    canActivate: [AuthGuard],           
    loadChildren: () => import('./shared/pages/Monitor/monitor.routes')
  },
  {
    path: 'Traceability',
    component: AppLayout,
    canActivate: [AuthGuard],            
    loadChildren: () => import('./shared/pages/Traceability/traceability.routes')
  },
  {
    path: 'CheckSerialError',
    component: AppLayout,
    canActivate: [AuthGuard],            
    loadChildren: () => import('./shared/pages/CheckSerialError/check-serial-error.routes')
  },
  {
    path: 'ScanSerialCheckLogs',
    component: AppLayout,
    canActivate: [AuthGuard],            
    loadChildren: () => import('./shared/pages/ScanSerialCheckLogs/scan-serial-check-log.routes')
  },
  { path: 'login', component: HomeComponent },
  { path: 'callback', component: CallbackComponent },
  { path: 'no-access', component: NoAccessComponent },
  // tùy chọn: redirect unknown
  { path: '**', redirectTo: '' }
];
