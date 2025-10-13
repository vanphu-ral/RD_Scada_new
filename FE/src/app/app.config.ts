import { ApplicationConfig, importProvidersFrom, provideBrowserGlobalErrorListeners, provideZonelessChangeDetection } from '@angular/core';
import { provideRouter, withEnabledBlockingInitialNavigation, withInMemoryScrolling } from '@angular/router';
import { routes } from './app.routes';
import { provideAnimations } from '@angular/platform-browser/animations';
import { providePrimeNG } from 'primeng/config';
import Lara from '@primeng/themes/lara';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { APP_INITIALIZER } from '@angular/core';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { initializeKeycloak } from './shared/core/auth/keycloak/keycloak-init.factory';

import { inject } from '@angular/core';
import { InMemoryCache, ApolloClientOptions } from '@apollo/client/core';
import { provideApollo } from 'apollo-angular'; // ✅ Quan trọng
import { HttpLink } from 'apollo-angular/http';
import { AccountService } from './shared/core/auth/account/account.service';
import { LoginService } from './shared/core/auth/login/login.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes, withInMemoryScrolling({ anchorScrolling: 'enabled', scrollPositionRestoration: 'enabled' }), withEnabledBlockingInitialNavigation()),
    provideHttpClient(withFetch()),
    provideAnimations(),
    provideAnimationsAsync(),
    providePrimeNG({ theme: { preset: Lara, options: { darkModeSelector: '.app-dark' } } }),
    provideBrowserGlobalErrorListeners(),
    provideZonelessChangeDetection(),
    providePrimeNG({
      translation: {
        // filter
        startsWith: 'Bắt đầu với',
        endsWith: 'Kết thúc với',
        contains: 'Chứa',
        notContains: 'Không chứa',
        equals: 'Bằng',
        notEquals: 'Không bằng',
        noFilter: 'Không lọc',
        dateIs: 'Ngày bằng',
        dateIsNot: 'Ngày khác',
        dateBefore: 'Trước ngày',
        dateAfter: 'Sau ngày',
        matchAll: 'Khớp tất cả',
        matchAny: 'Khớp bất kỳ',

        // calendar
        dayNamesMin: ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'],
        monthNames: [
          'Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
          'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'
        ],
        monthNamesShort: ['Th1', 'Th2', 'Th3', 'Th4', 'Th5', 'Th6', 'Th7', 'Th8', 'Th9', 'Th10', 'Th11', 'Th12'],
        today: 'Hôm nay',
        clear: 'Xóa',
        dateFormat: 'dd/mm/yy',
        firstDayOfWeek: 1,

        // multi select
        selectionMessage: '{0} cột được chọn'
      }
    }),
    {
      provide: APP_INITIALIZER,
      useFactory: (loginService: LoginService) => {
        return async () => {
          const currentPath = window.location.pathname;
          if (currentPath.startsWith('/callback')) {
            await loginService.handlePostLogin();
          } else {
            await loginService.restoreLoginFromStorage();
          }
        };
      },
      deps: [LoginService],
      multi: true
    },
    provideApollo((): ApolloClientOptions<any> => {
      const httpLink = inject(HttpLink); // 👈 inject thủ công HttpLink
      return {
        cache: new InMemoryCache(),
        link: httpLink.create({ uri: 'http://192.168.68.61:8081/graphql' }),
      };
    }),
  ]
};
