import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from './grade.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher';
import { ITimeTable } from 'app/shared/model/time-table.model';
import { TimeTableService } from 'app/entities/time-table';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
    selector: 'jhi-grade-update',
    templateUrl: './grade-update.component.html'
})
export class GradeUpdateComponent implements OnInit {
    grade: IGrade;
    isSaving: boolean;

    headteachers: ITeacher[];

    teachers: ITimeTable[];

    teachers: ITeacher[];

    students: IStudent[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected gradeService: GradeService,
        protected teacherService: TeacherService,
        protected timeTableService: TimeTableService,
        protected studentService: StudentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ grade }) => {
            this.grade = grade;
        });
        this.teacherService.query({ filter: 'grade-is-null' }).subscribe(
            (res: HttpResponse<ITeacher[]>) => {
                if (!this.grade.headTeacher || !this.grade.headTeacher.id) {
                    this.headteachers = res.body;
                } else {
                    this.teacherService.find(this.grade.headTeacher.id).subscribe(
                        (subRes: HttpResponse<ITeacher>) => {
                            this.headteachers = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.timeTableService.query({ filter: 'grade-is-null' }).subscribe(
            (res: HttpResponse<ITimeTable[]>) => {
                if (!this.grade.teacher || !this.grade.teacher.id) {
                    this.teachers = res.body;
                } else {
                    this.timeTableService.find(this.grade.teacher.id).subscribe(
                        (subRes: HttpResponse<ITimeTable>) => {
                            this.teachers = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.teacherService.query().subscribe(
            (res: HttpResponse<ITeacher[]>) => {
                this.teachers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.studentService.query().subscribe(
            (res: HttpResponse<IStudent[]>) => {
                this.students = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.grade.id !== undefined) {
            this.subscribeToSaveResponse(this.gradeService.update(this.grade));
        } else {
            this.subscribeToSaveResponse(this.gradeService.create(this.grade));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrade>>) {
        result.subscribe((res: HttpResponse<IGrade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStudentById(index: number, item: IStudent) {
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
