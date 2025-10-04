import { Routes } from '@angular/router';
import { MonitorPage } from './pages/List/monitor.page';
import { MonitorDetailPage } from './pages/Detail/monitor-detail.page';
import { PlanningWoResolve } from './Resolver/planning-wo.resolver';


const monitorRoute: Routes = [
  {
    path: '',
    component: MonitorPage,
  },
  {
    path: ':id/view',
    component: MonitorDetailPage,
    data: { mode: 'view' },
    resolve: {
      data: PlanningWoResolve,
    }
  }
];

export default monitorRoute;
