import { KeycloakService } from 'keycloak-angular';
import { AccountService } from '../account/account.service';
import { firstValueFrom } from 'rxjs';

export function initializeKeycloak(keycloak: KeycloakService, accountService: AccountService) {
    return async () => {
        await keycloak.init({
            config: {
                url: 'http://192.168.68.90:8080/auth',
                realm: 'QLSX',
                clientId: 'scada_giam_sat',
            },
            initOptions: {
                onLoad: 'check-sso', // 'check-sso' để không ép login, hoặc 'login-required' nếu bắt buộc
                checkLoginIframe: false,
                pkceMethod: 'S256'
            },
            enableBearerInterceptor: true,
            bearerExcludedUrls: ['/assets', '/api/public']
        });

        // Sau khi Keycloak init xong, load thông tin account (nếu có session)
        try {
            // đợi observable hoàn tất (identity trả Observable<Account|null>)
            await firstValueFrom(accountService.identity(true));
        } catch (e) {
            // nếu lỗi thì bỏ qua — app vẫn chạy
            console.warn('Không load được account sau khi init Keycloak', e);
        }
    };
}
