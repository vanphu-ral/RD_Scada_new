import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AccountService } from './account/account.service';
import { map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import _ from 'lodash';

export const AuthGuard: CanActivateFn = () => {
    const accountService = inject(AccountService);
    const router = inject(Router);

    return accountService.identity().pipe(
        map(account => {
            if (!account) {
                console.warn('⚠️ Chưa đăng nhập → chuyển login');
                router.navigate(['/login']);
                return false;
            }
            const roles = _.get(account, 'attributes.roles', []) || [];
            const allowedRoles = [
                'RD_GiamSat_TruySuatSerial_Admin',
                'RD_GiamSat_TruySuatSerial_View'
            ];
            const hasAccess = allowedRoles.some(r => _.includes(roles, r));
            if (!hasAccess) {
                console.warn('🚫 User không có quyền truy cập hệ thống này');
                router.navigate(['/no-access']);
                return false;
            }
            return true; 
        }),
        catchError(err => {
            console.error('❌ Lỗi khi xác thực:', err);
            router.navigate(['/login']);
            return of(false);
        })
    );
};
