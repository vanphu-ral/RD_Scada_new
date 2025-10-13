import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AccountService } from './account/account.service';
import { map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';

export const AuthGuard: CanActivateFn = () => {
    const accountService = inject(AccountService);
    const router = inject(Router);

    return accountService.identity().pipe(
        map(account => {
            console.log('✅ Account:', account);

            if (account) {
                return true; // Cho phép vào route
            } else {
                console.warn('⚠️ Chưa đăng nhập → chuyển login');
                router.navigate(['/login']);
                return false;
            }
        }),
        catchError(err => {
            console.error('❌ Lỗi khi xác thực:', err);
            router.navigate(['/login']);
            return of(false);
        })
    );
};
