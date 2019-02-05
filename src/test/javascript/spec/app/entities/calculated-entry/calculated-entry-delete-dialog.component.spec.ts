/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PeptidenotebookTestModule } from '../../../test.module';
import { CalculatedEntryDeleteDialogComponent } from 'app/entities/calculated-entry/calculated-entry-delete-dialog.component';
import { CalculatedEntryService } from 'app/entities/calculated-entry/calculated-entry.service';

describe('Component Tests', () => {
    describe('CalculatedEntry Management Delete Component', () => {
        let comp: CalculatedEntryDeleteDialogComponent;
        let fixture: ComponentFixture<CalculatedEntryDeleteDialogComponent>;
        let service: CalculatedEntryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [CalculatedEntryDeleteDialogComponent]
            })
                .overrideTemplate(CalculatedEntryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CalculatedEntryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalculatedEntryService);
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
