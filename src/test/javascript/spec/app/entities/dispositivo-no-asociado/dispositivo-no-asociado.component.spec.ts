/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ShaffiroTestModule } from '../../../test.module';
import { DispositivoNoAsociadoComponent } from 'app/entities/dispositivo-no-asociado/dispositivo-no-asociado.component';
import { DispositivoNoAsociadoService } from 'app/entities/dispositivo-no-asociado/dispositivo-no-asociado.service';
import { DispositivoNoAsociado } from 'app/shared/model/dispositivo-no-asociado.model';

describe('Component Tests', () => {
    describe('DispositivoNoAsociado Management Component', () => {
        let comp: DispositivoNoAsociadoComponent;
        let fixture: ComponentFixture<DispositivoNoAsociadoComponent>;
        let service: DispositivoNoAsociadoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShaffiroTestModule],
                declarations: [DispositivoNoAsociadoComponent],
                providers: []
            })
                .overrideTemplate(DispositivoNoAsociadoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DispositivoNoAsociadoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DispositivoNoAsociadoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DispositivoNoAsociado(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dispositivoNoAsociados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
