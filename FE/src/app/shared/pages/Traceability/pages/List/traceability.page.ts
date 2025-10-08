import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { SharedModule } from '../../../../../share.module';
import { HttpClient } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { PlanningWoService } from '../../../Monitor/service/planning-wo.service';

@Component({
    selector: 'app-traceability',
    standalone: true,
    templateUrl: './traceability.page.html',
    styleUrls: ['./traceability.page.scss'],
    imports: [CommonModule, SharedModule]
})
export class TraceabilityPage {

    data: any[] = [];
    filter: any = {
        serialBoard: '',
        serialItem: '',
    }
    optionSerial: any[] = [
        { name: 'Serial Board', value: 'serialBoard' },
        { name: 'Serial Item', value: 'serialItem' },
    ];
    products!: any[];
    constructor(private router: Router, private route: ActivatedRoute, private planningWoService: PlanningWoService) { }

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

    search() {
        if (this.filter.option == 'serialBoard') {
            this.filter.serialBoard = this.filter.serial;

            this.planningWoService.filterBySerialBoard(this.filter.serialBoard).subscribe((res: any) => {
                console.log(res);

                // this.products = res.data;
            })
        } else {
            this.filter.serialItem = this.filter.serial;
            this.planningWoService.filterBySerialItem(this.filter.serialItem).subscribe((res: any) => {
                console.log(res);

                // this.products = res.data;
            })
        }
    }

}