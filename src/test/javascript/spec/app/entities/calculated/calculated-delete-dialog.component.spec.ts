/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PeptidenotebookTestModule } from '../../../test.module';
import { CalculatedDeleteDialogComponent } from 'app/entities/calculated/calculated-delete-dialog.component';
import { CalculatedService } from 'app/entities/calculated/calculated.service';

describe('Component Tests', () => {
    describe('Calculated Management Delete Component', () => {
        let comp: CalculatedDeleteDialogComponent;
        let fixture: ComponentFixture<CalculatedDeleteDialogComponent>;
        let service: CalculatedService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [CalculatedDeleteDialogComponent]
            })
                .overrideTemplate(CalculatedDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CalculatedDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalculatedService);
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
