import { ITeacher } from 'app/shared/model//teacher.model';
import { ITimeTable } from 'app/shared/model//time-table.model';
import { IStudent } from 'app/shared/model//student.model';

export interface IGrade {
    id?: number;
    gradeName?: string;
    gradeAcademy?: string;
    gradeDepartment?: string;
    headTeacher?: ITeacher;
    teacher?: ITimeTable;
    teachers?: ITeacher[];
    studentName?: IStudent;
    studentNames?: IStudent[];
}

export class Grade implements IGrade {
    constructor(
        public id?: number,
        public gradeName?: string,
        public gradeAcademy?: string,
        public gradeDepartment?: string,
        public headTeacher?: ITeacher,
        public teacher?: ITimeTable,
        public teachers?: ITeacher[],
        public studentName?: IStudent,
        public studentNames?: IStudent[]
    ) {}
}
