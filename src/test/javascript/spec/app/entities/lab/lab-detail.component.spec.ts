/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemoTestModule } from '../../../test.module';
import { LabDetailComponent } from 'app/entities/lab/lab-detail.component';
import { Lab } from 'app/shared/model/lab.model';

describe('Component Tests', () => {
    describe('Lab Management Detail Component', () => {
        let comp: LabDetailComponent;
        let fixture: ComponentFixture<LabDetailComponent>;
        const route = ({ data: of({ lab: new Lab(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DemoTestModule],
                declarations: [LabDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LabDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LabDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lab).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
