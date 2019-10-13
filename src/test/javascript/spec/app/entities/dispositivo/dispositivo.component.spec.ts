/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ShaffiroTestModule } from '../../../test.module';
import { DispositivoComponent } from 'app/entities/dispositivo/dispositivo.component';
import { DispositivoService } from 'app/entities/dispositivo/dispositivo.service';
import { Dispositivo } from 'app/shared/model/dispositivo.model';

describe('Component Tests', () => {
    describe('Dispositivo Management Component', () => {
        let comp: DispositivoComponent;
        let fixture: ComponentFixture<DispositivoComponent>;
        let service: DispositivoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShaffiroTestModule],
                declarations: [DispositivoComponent],
                providers: []
            })
                .overrideTemplate(DispositivoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DispositivoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DispositivoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Dispositivo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dispositivos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
