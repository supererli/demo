import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILab } from 'app/shared/model/lab.model';
import { LabService } from './lab.service';
import { ITimeTable } from 'app/shared/model/time-table.model';
import { TimeTableService } from 'app/entities/time-table';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course';

@Component({
    selector: 'jhi-lab-update',
    templateUrl: './lab-update.component.html'
})
export class LabUpdateComponent implements OnInit {
    lab: ILab;
    isSaving: boolean;

    timetables: ITimeTable[];

    courses: ICourse[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected labService: LabService,
        protected timeTableService: TimeTableService,
        protected courseService: CourseService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lab }) => {
            this.lab = lab;
        });
        this.timeTableService.query({ filter: 'lab-is-null' }).subscribe(
            (res: HttpResponse<ITimeTable[]>) => {
                if (!this.lab.timeTable || !this.lab.timeTable.id) {
                    this.timetables = res.body;
                } else {
                    this.timeTableService.find(this.lab.timeTable.id).subscribe(
                        (subRes: HttpResponse<ITimeTable>) => {
                            this.timetables = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    trackTimeTableById(index: number, item: ITimeTable) {
        return item.id;
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
