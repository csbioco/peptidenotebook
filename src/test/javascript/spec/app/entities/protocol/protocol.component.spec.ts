/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PeptidenotebookTestModule } from '../../../test.module';
import { ProtocolComponent } from 'app/entities/protocol/protocol.component';
import { ProtocolService } from 'app/entities/protocol/protocol.service';
import { Protocol } from 'app/shared/model/protocol.model';

describe('Component Tests', () => {
    describe('Protocol Management Component', () => {
        let comp: ProtocolComponent;
        let fixture: ComponentFixture<ProtocolComponent>;
        let service: ProtocolService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [ProtocolComponent],
                providers: []
            })
                .overrideTemplate(ProtocolComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProtocolComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProtocolService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Protocol(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.protocols[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
