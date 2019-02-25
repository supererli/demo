import { ITimeTable } from 'app/shared/model//time-table.model';
import { IUser } from 'app/core/user/user.model';
import { ICourse } from 'app/shared/model//course.model';
import { IGrade } from 'app/shared/model//grade.model';

export interface ITeacher {
    id?: number;
    teacherNo?: string;
    teacherName?: string;
    subject?: string;
    timeTable?: string;
    teacherTel?: string;
    timeTables?: ITimeTable[];
    user?: IUser;
    subjects?: ICourse[];
    grades?: IGrade[];
}

export class Teacher implements ITeacher {
    constructor(
        public id?: number,
        public teacherNo?: string,
        public teacherName?: string,
        public subject?: string,
        public timeTable?: string,
        public teacherTel?: string,
        public timeTables?: ITimeTable[],
        public user?: IUser,
        public subjects?: ICourse[],
        public grades?: IGrade[]
    ) {}
}
