/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DemoTestModule } from '../../../test.module';
import { LabUpdateComponent } from 'app/entities/lab/lab-update.component';
import { LabService } from 'app/entities/lab/lab.service';
import { Lab } from 'app/shared/model/lab.model';

describe('Component Tests', () => {
    describe('Lab Management Update Component', () => {
        let comp: LabUpdateComponent;
        let fixture: ComponentFixture<LabUpdateComponent>;
        let service: LabService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DemoTestModule],
                declarations: [LabUpdateComponent]
            })
                .overrideTemplate(LabUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LabUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LabService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Lab(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lab = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Lab();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lab = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
