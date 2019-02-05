/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { CalculatedEntryService } from 'app/entities/calculated-entry/calculated-entry.service';
import { ICalculatedEntry, CalculatedEntry } from 'app/shared/model/calculated-entry.model';

describe('Service Tests', () => {
    describe('CalculatedEntry Service', () => {
        let injector: TestBed;
        let service: CalculatedEntryService;
        let httpMock: HttpTestingController;
        let elemDefault: ICalculatedEntry;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CalculatedEntryService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new CalculatedEntry(0, 0, 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA');
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

            it('should create a CalculatedEntry', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new CalculatedEntry(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CalculatedEntry', async () => {
                const returnedFromService = Object.assign(
                    {
                        sequencenumber: 1,
                        aa: 'BBBBBB',
                        sc: 1,
                        dc: 1,
                        scale: 1,
                        mwwithprotection: 1,
                        mwwithoutprotection: 1,
                        eachaaweight: 1,
                        difficulty: 1,
                        currentresinweight: 1,
                        protocolname: 'BBBBBB'
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

            it('should return a list of CalculatedEntry', async () => {
                const returnedFromService = Object.assign(
                    {
                        sequencenumber: 1,
                        aa: 'BBBBBB',
                        sc: 1,
                        dc: 1,
                        scale: 1,
                        mwwithprotection: 1,
                        mwwithoutprotection: 1,
                        eachaaweight: 1,
                        difficulty: 1,
                        currentresinweight: 1,
                        protocolname: 'BBBBBB'
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

            it('should delete a CalculatedEntry', async () => {
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
