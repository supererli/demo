import { ITeacher } from 'app/shared/model//teacher.model';
import { ILab } from 'app/shared/model//lab.model';

export interface ICourse {
    id?: number;
    courseName?: string;
    courseType?: string;
    teacher?: ITeacher;
    labs?: ILab[];
}

export class Course implements ICourse {
    constructor(
        public id?: number,
        public courseName?: string,
        public courseType?: string,
        public teacher?: ITeacher,
        public labs?: ILab[]
    ) {}
}
