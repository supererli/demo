import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILab } from 'app/shared/model/lab.model';

@Component({
    selector: 'jhi-lab-detail',
    templateUrl: './lab-detail.component.html'
})
export class LabDetailComponent implements OnInit {
    lab: ILab;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lab }) => {
            this.lab = lab;
        });
    }

    previousState() {
        window.history.back();
    }
}
