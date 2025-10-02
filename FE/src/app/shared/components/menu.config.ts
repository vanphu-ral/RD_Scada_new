import { MenuItem } from 'primeng/api';

export const MENU_ITEMS: MenuItem[] = [
    {
        items: [
            { label: 'Giám sát', icon: 'pi pi-gauge', routerLink: ['/'] },
            { label: 'Truy suất nguồn gốc', icon: 'fas fa-calendar-alt', routerLink: ['/Traceability'] },
        ]
    }
];