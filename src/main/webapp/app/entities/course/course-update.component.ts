import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from './course.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher';
import { ITimeTable } from 'app/shared/model/time-table.model';
import { TimeTableService } from 'app/entities/time-table';
import { ILab } from 'app/shared/model/lab.model';
import { LabService } from 'app/entities/lab';

@Component({
    selector: 'jhi-course-update',
    templateUrl: './course-update.component.html'
})
export class CourseUpdateComponent implements OnInit {
    course: ICourse;
    isSaving: boolean;

    teachers: ITeacher[];

    timetables: ITimeTable[];

    labs: ILab[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected courseService: CourseService,
        protected teacherService: TeacherService,
        protected timeTableService: TimeTableService,
        protected labService: LabService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ course }) => {
            this.course = course;
        });
        this.teacherService.query().subscribe(
            (res: HttpResponse<ITeacher[]>) => {
                this.teachers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.timeTableService.query().subscribe(
            (res: HttpResponse<ITimeTable[]>) => {
                this.timetables = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.labService.query().subscribe(
            (res: HttpResponse<ILab[]>) => {
                this.labs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.course.id !== undefined) {
            this.subscribeToSaveResponse(this.courseService.update(this.course));
        } else {
            this.subscribeToSaveResponse(this.courseService.create(this.course));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourse>>) {
        result.subscribe((res: HttpResponse<ICourse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTimeTableById(index: number, item: ITimeTable) {
        return item.id;
    }

    trackLabById(index: number, item: ILab) {
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
