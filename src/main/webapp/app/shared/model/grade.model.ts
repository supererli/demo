import { Moment } from 'moment';
import { ITeacher } from 'app/shared/model//teacher.model';
import { IStudent } from 'app/shared/model//student.model';

export interface IGrade {
    id?: number;
    gradeName?: string;
    gradeAcademy?: string;
    gradeDepartment?: string;
    headTeacher?: string;
    gradeTime?: Moment;
    teachers?: ITeacher[];
    students?: IStudent[];
}

export class Grade implements IGrade {
    constructor(
        public id?: number,
        public gradeName?: string,
        public gradeAcademy?: string,
        public gradeDepartment?: string,
        public headTeacher?: string,
        public gradeTime?: Moment,
        public teachers?: ITeacher[],
        public students?: IStudent[]
    ) {}
}
