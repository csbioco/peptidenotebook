/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PeptidenotebookTestModule } from '../../../test.module';
import { ReagentDeleteDialogComponent } from 'app/entities/reagent/reagent-delete-dialog.component';
import { ReagentService } from 'app/entities/reagent/reagent.service';

describe('Component Tests', () => {
    describe('Reagent Management Delete Component', () => {
        let comp: ReagentDeleteDialogComponent;
        let fixture: ComponentFixture<ReagentDeleteDialogComponent>;
        let service: ReagentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [ReagentDeleteDialogComponent]
            })
                .overrideTemplate(ReagentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReagentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReagentService);
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
