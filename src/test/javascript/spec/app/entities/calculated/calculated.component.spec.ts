/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PeptidenotebookTestModule } from '../../../test.module';
import { CalculatedComponent } from 'app/entities/calculated/calculated.component';
import { CalculatedService } from 'app/entities/calculated/calculated.service';
import { Calculated } from 'app/shared/model/calculated.model';

describe('Component Tests', () => {
    describe('Calculated Management Component', () => {
        let comp: CalculatedComponent;
        let fixture: ComponentFixture<CalculatedComponent>;
        let service: CalculatedService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PeptidenotebookTestModule],
                declarations: [CalculatedComponent],
                providers: []
            })
                .overrideTemplate(CalculatedComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CalculatedComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalculatedService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Calculated(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.calculateds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
