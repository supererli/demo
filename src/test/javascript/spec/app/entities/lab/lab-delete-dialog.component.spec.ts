/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DemoTestModule } from '../../../test.module';
import { LabDeleteDialogComponent } from 'app/entities/lab/lab-delete-dialog.component';
import { LabService } from 'app/entities/lab/lab.service';

describe('Component Tests', () => {
    describe('Lab Management Delete Component', () => {
        let comp: LabDeleteDialogComponent;
        let fixture: ComponentFixture<LabDeleteDialogComponent>;
        let service: LabService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DemoTestModule],
                declarations: [LabDeleteDialogComponent]
            })
                .overrideTemplate(LabDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LabDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LabService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
