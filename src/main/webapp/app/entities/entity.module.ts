import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DemoCourseModule } from './course/course.module';
import { DemoTeacherModule } from './teacher/teacher.module';
import { DemoGradeModule } from './grade/grade.module';
import { DemoLabModule } from './lab/lab.module';
import { DemoStudentModule } from './student/student.module';
import { DemoTimeTableModule } from './time-table/time-table.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        DemoCourseModule,
        DemoTeacherModule,
        DemoGradeModule,
        DemoLabModule,
        DemoStudentModule,
        DemoTimeTableModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DemoEntityModule {}
