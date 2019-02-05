/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PeptidenotebookTestModule } from '../../../test.module';
import { ProtocolentryUpdateComponent } from 'app/entities/protocolentry/protocolentry-update.component';
import { ProtocolentryService } from 'app/entities/protocolentry/protocolentry.service';
import { Protocolentry } from 'app/shared/model/protocolentry.model';

describe('Component Tests', () => {
    describe('Protocolentry Management Update Component', () => {
        let comp: ProtocolentryUpdateComponent;
        let fixture: ComponentFixture<ProtocolentryUpdateComponent>;
        let service: ProtocolentryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [ProtocolentryUpdateComponent]
            })
                .overrideTemplate(ProtocolentryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProtocolentryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProtocolentryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Protocolentry(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.protocolentry = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Protocolentry();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.protocolentry = entity;
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
