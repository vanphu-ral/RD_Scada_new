import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { SharedModule } from '../../../../../share.module';
import { HttpClient } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-traceability',
    standalone: true,
    templateUrl: './traceability.page.html',
    styleUrls: ['./traceability.page.scss'],
    imports: [CommonModule, SharedModule]
})
export class TraceabilityPage {
    data: any[] = [];
    products!: any[];
    constructor(private router: Router, private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.products = [
            {
                id: '1000',
                code: 'f230fh0g3',
                name: 'Bamboo Watch',
                description: 'Product Description',
                image: 'bamboo-watch.jpg',
                price: 65,
                category: 'Accessories',
                quantity: 24,
                inventoryStatus: 'INSTOCK',
                rating: 5
            },
            {
                id: '1000',
                code: 'f230fh0g3',
                name: 'Bamboo Watch',
                description: 'Product Description',
                image: 'bamboo-watch.jpg',
                price: 65,
                category: 'Accessories',
                quantity: 24,
                inventoryStatus: 'INSTOCK',
                rating: 5
            },
            {
                id: '1000',
                code: 'f230fh0g3',
                name: 'Bamboo Watch',
                description: 'Product Description',
                image: 'bamboo-watch.jpg',
                price: 65,
                category: 'Accessories',
                quantity: 24,
                inventoryStatus: 'INSTOCK',
                rating: 5
            },
            {
                id: '1000',
                code: 'f230fh0g3',
                name: 'Bamboo Watch',
                description: 'Product Description',
                image: 'bamboo-watch.jpg',
                price: 65,
                category: 'Accessories',
                quantity: 24,
                inventoryStatus: 'INSTOCK',
                rating: 5
            },
        ]
    }

    viewItem(item: any) {
        this.router.navigate([item.id, 'view'], { relativeTo: this.route });
    }

}