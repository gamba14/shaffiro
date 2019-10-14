/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ShaffiroTestModule } from '../../../test.module';
import { DispositivoNoAsociadoUpdateComponent } from 'app/entities/dispositivo-no-asociado/dispositivo-no-asociado-update.component';
import { DispositivoNoAsociadoService } from 'app/entities/dispositivo-no-asociado/dispositivo-no-asociado.service';
import { DispositivoNoAsociado } from 'app/shared/model/dispositivo-no-asociado.model';

describe('Component Tests', () => {
    describe('DispositivoNoAsociado Management Update Component', () => {
        let comp: DispositivoNoAsociadoUpdateComponent;
        let fixture: ComponentFixture<DispositivoNoAsociadoUpdateComponent>;
        let service: DispositivoNoAsociadoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShaffiroTestModule],
                declarations: [DispositivoNoAsociadoUpdateComponent]
            })
                .overrideTemplate(DispositivoNoAsociadoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DispositivoNoAsociadoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DispositivoNoAsociadoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DispositivoNoAsociado(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dispositivoNoAsociado = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DispositivoNoAsociado();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dispositivoNoAsociado = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
