import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Lab } from 'app/shared/model/lab.model';
import { LabService } from './lab.service';
import { LabComponent } from './lab.component';
import { LabDetailComponent } from './lab-detail.component';
import { LabUpdateComponent } from './lab-update.component';
import { LabDeletePopupComponent } from './lab-delete-dialog.component';
import { ILab } from 'app/shared/model/lab.model';

@Injectable({ providedIn: 'root' })
export class LabResolve implements Resolve<ILab> {
    constructor(private service: LabService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Lab> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Lab>) => response.ok),
                map((lab: HttpResponse<Lab>) => lab.body)
            );
        }
        return of(new Lab());
    }
}

export const labRoute: Routes = [
    {
        path: 'lab',
        component: LabComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'demoApp.lab.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lab/:id/view',
        component: LabDetailComponent,
        resolve: {
            lab: LabResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'demoApp.lab.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lab/new',
        component: LabUpdateComponent,
        resolve: {
            lab: LabResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'demoApp.lab.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lab/:id/edit',
        component: LabUpdateComponent,
        resolve: {
            lab: LabResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'demoApp.lab.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const labPopupRoute: Routes = [
    {
        path: 'lab/:id/delete',
        component: LabDeletePopupComponent,
        resolve: {
            lab: LabResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'demoApp.lab.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
