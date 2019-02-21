import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ICourse } from 'app/shared/model//course.model';
import { IGrade } from 'app/shared/model//grade.model';

export interface ITeacher {
    id?: number;
    teacherNo?: string;
    teacherName?: string;
    teacherTel?: string;
    teacherTime?: Moment;
    user?: IUser;
    courses?: ICourse[];
    grades?: IGrade[];
}

export class Teacher implements ITeacher {
    constructor(
        public id?: number,
        public teacherNo?: string,
        public teacherName?: string,
        public teacherTel?: string,
        public teacherTime?: Moment,
        public user?: IUser,
        public courses?: ICourse[],
        public grades?: IGrade[]
    ) {}
}
