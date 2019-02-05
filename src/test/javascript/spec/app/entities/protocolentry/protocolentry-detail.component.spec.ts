/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeptidenotebookTestModule } from '../../../test.module';
import { ProtocolentryDetailComponent } from 'app/entities/protocolentry/protocolentry-detail.component';
import { Protocolentry } from 'app/shared/model/protocolentry.model';

describe('Component Tests', () => {
    describe('Protocolentry Management Detail Component', () => {
        let comp: ProtocolentryDetailComponent;
        let fixture: ComponentFixture<ProtocolentryDetailComponent>;
        const route = ({ data: of({ protocolentry: new Protocolentry(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [ProtocolentryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProtocolentryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProtocolentryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.protocolentry).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
