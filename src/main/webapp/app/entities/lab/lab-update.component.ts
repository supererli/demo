import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ILab } from 'app/shared/model/lab.model';
import { LabService } from './lab.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';

@Component({
    selector: 'jhi-lab-update',
    templateUrl: './lab-update.component.html'
})
export class LabUpdateComponent implements OnInit {
    lab: ILab;
    isSaving: boolean;

    courses: ICourse[];
    labTime: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected labService: LabService,
        protected courseService: CourseService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lab }) => {
            this.lab = lab;
            this.labTime = this.lab.labTime != null ? this.lab.labTime.format(DATE_TIME_FORMAT) : null;
        });
        this.courseService.query().subscribe(
            (res: HttpResponse<ICourse[]>) => {
                this.courses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.lab.labTime = this.labTime != null ? moment(this.labTime, DATE_TIME_FORMAT) : null;
        if (this.lab.id !== undefined) {
            this.subscribeToSaveResponse(this.labService.update(this.lab));
        } else {
            this.subscribeToSaveResponse(this.labService.create(this.lab));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILab>>) {
        result.subscribe((res: HttpResponse<ILab>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCourseById(index: number, item: ICourse) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
