import { Routes } from '@angular/router';
import { AppComponent } from './app';
import { AppLayout } from './shared/layout/app.layout';
import { CallbackComponent } from './shared/core/auth/callback.component';

export const routes: Routes = [
    {
        path: '',
        component: AppLayout,
        // children: [
        //     { path: '', component: MonitorPage }
        // ]
        loadChildren: () => import('./shared/pages/Monitor/monitor.routes')
    },
    {
        path: 'Traceability',
        component: AppLayout,
        loadChildren: () => import('./shared/pages/Traceability/traceability.routes')
    },
    { path: 'callback', component: CallbackComponent }
];
