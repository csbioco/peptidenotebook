/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { AasService } from 'app/entities/aas/aas.service';
import { IAas, Aas } from 'app/shared/model/aas.model';

describe('Service Tests', () => {
    describe('Aas Service', () => {
        let injector: TestBed;
        let service: AasService;
        let httpMock: HttpTestingController;
        let elemDefault: IAas;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(AasService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Aas(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Aas', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Aas(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Aas', async () => {
                const returnedFromService = Object.assign(
                    {
                        aaname: 'BBBBBB',
                        threeletter: 'BBBBBB',
                        mwwithprotection: 1,
                        mwwithoutprotection: 1,
                        pi: 1,
                        unitprice: 1,
                        difficulty: 1,
                        numc: 1,
                        numh: 1,
                        numn: 1,
                        numo: 1,
                        nums: 1,
                        solubility: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Aas', async () => {
                const returnedFromService = Object.assign(
                    {
                        aaname: 'BBBBBB',
                        threeletter: 'BBBBBB',
                        mwwithprotection: 1,
                        mwwithoutprotection: 1,
                        pi: 1,
                        unitprice: 1,
                        difficulty: 1,
                        numc: 1,
                        numh: 1,
                        numn: 1,
                        numo: 1,
                        nums: 1,
                        solubility: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Aas', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
