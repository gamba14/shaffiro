/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShaffiroTestModule } from '../../../test.module';
import { DispositivoNoAsociadoDetailComponent } from 'app/entities/dispositivo-no-asociado/dispositivo-no-asociado-detail.component';
import { DispositivoNoAsociado } from 'app/shared/model/dispositivo-no-asociado.model';

describe('Component Tests', () => {
    describe('DispositivoNoAsociado Management Detail Component', () => {
        let comp: DispositivoNoAsociadoDetailComponent;
        let fixture: ComponentFixture<DispositivoNoAsociadoDetailComponent>;
        const route = ({ data: of({ dispositivoNoAsociado: new DispositivoNoAsociado(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShaffiroTestModule],
                declarations: [DispositivoNoAsociadoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DispositivoNoAsociadoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DispositivoNoAsociadoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dispositivoNoAsociado).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
