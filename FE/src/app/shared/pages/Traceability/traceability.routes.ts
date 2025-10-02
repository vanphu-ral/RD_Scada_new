import { Routes } from '@angular/router';
import { TraceabilityPage } from './pages/List/traceability.page';
import { MonitorDetailPage } from './pages/Detail/monitor-detail.page';


const traceabilityRoute: Routes = [
  {
    path: '',
    component: TraceabilityPage,
  }
];

export default traceabilityRoute;
