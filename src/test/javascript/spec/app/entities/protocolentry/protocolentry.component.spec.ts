/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PeptidenotebookTestModule } from '../../../test.module';
import { ProtocolentryComponent } from 'app/entities/protocolentry/protocolentry.component';
import { ProtocolentryService } from 'app/entities/protocolentry/protocolentry.service';
import { Protocolentry } from 'app/shared/model/protocolentry.model';

describe('Component Tests', () => {
    describe('Protocolentry Management Component', () => {
        let comp: ProtocolentryComponent;
        let fixture: ComponentFixture<ProtocolentryComponent>;
        let service: ProtocolentryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [ProtocolentryComponent],
                providers: []
            })
                .overrideTemplate(ProtocolentryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProtocolentryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProtocolentryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Protocolentry(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.protocolentries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
