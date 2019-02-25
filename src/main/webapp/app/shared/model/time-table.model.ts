import { ITeacher } from 'app/shared/model//teacher.model';

export interface ITimeTable {
    id?: number;
    weekday?: string;
    number?: number;
    teacher?: ITeacher;
}

export class TimeTable implements ITimeTable {
    constructor(public id?: number, public weekday?: string, public number?: number, public teacher?: ITeacher) {}
}
