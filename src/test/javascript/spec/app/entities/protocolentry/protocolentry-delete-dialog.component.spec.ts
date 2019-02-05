/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PeptidenotebookTestModule } from '../../../test.module';
import { ProtocolentryDeleteDialogComponent } from 'app/entities/protocolentry/protocolentry-delete-dialog.component';
import { ProtocolentryService } from 'app/entities/protocolentry/protocolentry.service';

describe('Component Tests', () => {
    describe('Protocolentry Management Delete Component', () => {
        let comp: ProtocolentryDeleteDialogComponent;
        let fixture: ComponentFixture<ProtocolentryDeleteDialogComponent>;
        let service: ProtocolentryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [ProtocolentryDeleteDialogComponent]
            })
                .overrideTemplate(ProtocolentryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProtocolentryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProtocolentryService);
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
