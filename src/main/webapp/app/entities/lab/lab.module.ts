import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DemoSharedModule } from 'app/shared';
import {
    LabComponent,
    LabDetailComponent,
    LabUpdateComponent,
    LabDeletePopupComponent,
    LabDeleteDialogComponent,
    labRoute,
    labPopupRoute
} from './';

const ENTITY_STATES = [...labRoute, ...labPopupRoute];

@NgModule({
    imports: [DemoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LabComponent, LabDetailComponent, LabUpdateComponent, LabDeleteDialogComponent, LabDeletePopupComponent],
    entryComponents: [LabComponent, LabUpdateComponent, LabDeleteDialogComponent, LabDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DemoLabModule {}
