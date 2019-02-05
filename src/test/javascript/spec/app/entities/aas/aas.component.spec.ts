/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PeptidenotebookTestModule } from '../../../test.module';
import { AasComponent } from 'app/entities/aas/aas.component';
import { AasService } from 'app/entities/aas/aas.service';
import { Aas } from 'app/shared/model/aas.model';

describe('Component Tests', () => {
    describe('Aas Management Component', () => {
        let comp: AasComponent;
        let fixture: ComponentFixture<AasComponent>;
        let service: AasService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [AasComponent],
                providers: []
            })
                .overrideTemplate(AasComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AasComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AasService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Aas(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.aas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
