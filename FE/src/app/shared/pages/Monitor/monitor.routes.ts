import { Routes } from '@angular/router';
import { MonitorPage } from './pages/List/monitor.page';
import { MonitorDetailPage } from './pages/Detail/monitor-detail.page';


const monitorRoute: Routes = [
  {
    path: '',
    component: MonitorPage,
  },
  {
    path: ':id/view',
    component: MonitorDetailPage
  }
];

export default monitorRoute;
