/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeptidenotebookTestModule } from '../../../test.module';
import { ProtocolDetailComponent } from 'app/entities/protocol/protocol-detail.component';
import { Protocol } from 'app/shared/model/protocol.model';

describe('Component Tests', () => {
    describe('Protocol Management Detail Component', () => {
        let comp: ProtocolDetailComponent;
        let fixture: ComponentFixture<ProtocolDetailComponent>;
        const route = ({ data: of({ protocol: new Protocol(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [ProtocolDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProtocolDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProtocolDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.protocol).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
