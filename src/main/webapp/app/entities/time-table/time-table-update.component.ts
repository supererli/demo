import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITimeTable } from 'app/shared/model/time-table.model';
import { TimeTableService } from './time-table.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher';

@Component({
    selector: 'jhi-time-table-update',
    templateUrl: './time-table-update.component.html'
})
export class TimeTableUpdateComponent implements OnInit {
    timeTable: ITimeTable;
    isSaving: boolean;

    teachers: ITeacher[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected timeTableService: TimeTableService,
        protected teacherService: TeacherService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ timeTable }) => {
            this.timeTable = timeTable;
        });
        this.teacherService.query().subscribe(
            (res: HttpResponse<ITeacher[]>) => {
                this.teachers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.timeTable.id !== undefined) {
            this.subscribeToSaveResponse(this.timeTableService.update(this.timeTable));
        } else {
            this.subscribeToSaveResponse(this.timeTableService.create(this.timeTable));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeTable>>) {
        result.subscribe((res: HttpResponse<ITimeTable>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTeacherById(index: number, item: ITeacher) {
        return item.id;
    }
}
