import { IGrade } from 'app/shared/model//grade.model';

export interface IStudent {
    id?: number;
    name?: string;
    stuNo?: string;
    grade?: IGrade;
}

export class Student implements IStudent {
    constructor(public id?: number, public name?: string, public stuNo?: string, public grade?: IGrade) {}
}
